package ch.zrhdev.spring.bestpractices.cloud;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("cloud")
@Service
public class CloudFoundryInterface {

    private static final Logger logger = LoggerFactory.getLogger(CloudFoundryInterface.class);

    private CloudFoundryInterface() {
    }

    /**
     * Search in VCAPP_SERVICES for a specific user provided service
     *
     * @param serviceName
     * @return com.fasterxml.jackson.databind.JsonNode
     */
    public static JsonNode getUserProvidedService(String serviceType, String serviceName) {

        // Initialize the return object
        JsonNode userProvided = null;

        // Get VCAP Services
        String vcapServicesString = "{}"; // Default Value = Empty Json
        try {
            // Read the System Env to get the VCAP_SERVICES
            vcapServicesString = System.getenv("VCAP_SERVICES");
        } catch (NullPointerException exe) {
            logger.error("Could not find the vcap environment variable.");
        }

        try {
            // Check if vcap services are available
            if (vcapServicesString != null) {

                // Parse the VCAP_SERVICES as Json Node
                ObjectMapper mapper = new ObjectMapper();
                JsonNode vcapServices = mapper.readTree(vcapServicesString);
                logger.debug("Initialized the VCAP_SERVICES as Json Array");

                userProvided = searchServiceByNameInVcappServices(vcapServices, serviceType, serviceName);

            } else {
                logger.debug("No vcap services found in current environment. Do you run the application locally?");
            }
        } catch (Exception e) {
            logger.error("Failed to parse the VCAP_SERVICES json.", e);
        }
        return userProvided;
    }

    private static JsonNode searchServiceByNameInVcappServices(JsonNode vcapServices, String serviceType, String serviceName) {

        JsonNode userProvidedService = null;

        try {
            //  Check vcap services if user-provided services are available
            if (vcapServices.has(serviceType)) {
                // Get All User Provided Services as Json Array
                JsonNode userProvidedServices = vcapServices.get(serviceType);
                logger.debug("{} Services: {}", serviceType, userProvidedServices.size());

                // Check the amount of User Provided Service found in VCAP Services
                if (userProvidedServices.size() > 0) {
                    // Parse all Services and search for given Service Name
                    for (JsonNode singleService : userProvidedServices) {
                        if (singleService.get("name").asText().equals(serviceName)) {
                            userProvidedService = singleService;
                            logger.debug("Found Service in VCAP_SERVICES: {}", serviceName);
                        }
                    }
                }
            } else {
                logger.debug("No {} services found in vcap_services", serviceType);
            }
        } catch (Exception e) {
            logger.error("Failed to find service with service_name={} from service_type={} in the vcap services object", serviceName, serviceType);
        }
        return userProvidedService;
    }
}
