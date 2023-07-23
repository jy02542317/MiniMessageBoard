package learning.java.minimessageboard.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    /**
     * SpringDoc 标题、描述、版本等信息配置
     *
     * @return openApi 配置信息
     */
    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("MiniMessageBoard API")
                        .description("MiniMessageBoard接口文档说明")
                        .version("v0.1"))
                        .externalDocs(documentation())
                      /*  .license(new License().name("YiYi项目博客专栏")
                                .url("https://blog.csdn.net/weihao0240/category_12166012.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("码云项目地址")
                        .url("https://gitee.com/jack0240/YiYi"))*/
                // 配置Authorizations
                .components(new Components().addSecuritySchemes("Authorization", new SecurityScheme().name("认证").type(SecurityScheme.Type.HTTP)
                                .description("JWT认证").scheme("bearer").bearerFormat("JWT")));
    }
    public ExternalDocumentation documentation() {
        return new ExternalDocumentation().description("文档描述").url("文档地址");
    }
    /**
     * demo 分组
     *
     * @return demo分组接口
     */
    @Bean
    public GroupedOpenApi UserApi() {
        return GroupedOpenApi.builder()
                .group("user接口")
                .pathsToMatch("/api/User/**","/api/Room/**","/api/Message/**","/api/File/**")
                .build();
    }
}
