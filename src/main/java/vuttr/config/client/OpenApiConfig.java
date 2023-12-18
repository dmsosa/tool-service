package vuttr.config.client;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@OpenAPIDefinition(
        info = @Info(
                title = "Tool API",
                description = "Very Useful Tools to Remember, this API can manage all the information regarding the tools you use the most",
                version = "${spring.application.version}",
                contact = @Contact(
                        name = "Duvi",
                        url = "https://www.instagram.com/duvi_official/",
                        email = "dmsosa21@outlook.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "By using this API, you agree to be entitled to nothing fancy but enjoying it"

        ),
        servers = {
                @Server(
                        url = "http://{host}:{port}",
                        description = "Dev Server",
                        variables = {
                                @ServerVariable(
                                        name = "host",
                                        description = "host were our server is running in",
                                        defaultValue = "localhost"
                                ),
                                @ServerVariable(
                                        name = "port",
                                        description = "port of our host were our server runs",
                                        allowableValues = {
                                                "8081", "443"
                                        },
                                        defaultValue = "8081"
                                )
                        }
                )
        }
)
public class OpenApiConfig {
}
