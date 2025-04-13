package com.ecom.security.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties
@Getter
@Setter
public class CustomProperties {

    private Server server;
    private Spring spring;
    private Eureka eureka;
    private Kafka kafka;
    private Logging logging;

    @Getter
    @Setter
    public static class Server {
        private Integer port;
    }

    @Getter
    @Setter
    public static class Spring {
        private Application application;
        private Mvc mvc;

        @Getter
        @Setter
        public static class Mvc {
            private Cors cors;

            @Getter
            @Setter
            public static class Cors {
                private String allowedOrigins;
                private String allowedMethods;
                private String allowedHeaders;
            }
        }
        private Security security;

        @Getter
        @Setter
        public static class Application {
            private String name;
        }

        @Getter
        @Setter
        public static class Security {
            private Oauth2 oauth2;

            @Getter
            @Setter
            public static class Oauth2 {
                private Client client;
                private Resourceserver resourceserver;

                @Getter
                @Setter
                public static class Client {
                    private Registration registration;
                    private Provider provider;

                    @Getter
                    @Setter
                    public static class Registration {
                        private Keycloak keycloak;

                        @Getter
                        @Setter
                        public static class Keycloak {
                            private String realm;
                            private String authServerUrl;
                            private String clientId;
                            private String clientSecret;
                            private String authorizationGrantType;
                            private String scope;
                        }
                    }

                    @Getter
                    @Setter
                    public static class Provider {
                        private String issuerUri;
                    }
                }

                @Getter
                @Setter
                public static class Resourceserver {
                    private Jwt jwt;

                    @Getter
                    @Setter
                    public static class Jwt {
                        private String issuerUri;
                    }
                }
            }
        }
    }

    @Getter
    @Setter
    public static class Eureka {
        private Client client;
        private Instance instance;

        @Getter
        @Setter
        public static class Client {
            private ServiceUrl serviceUrl;
            private boolean fetchRegistry;
            private boolean registerWithEureka;

            @Getter
            @Setter
            public static class ServiceUrl {
                private String defaultZone;
            }
        }

        @Getter
        @Setter
        public static class Instance {
            private boolean preferIpAddress;
        }
    }

    @Getter
    @Setter
    public static class Kafka {
        private String bootstrapServers;
        private Consumer consumer;
        private Producer producer;

        @Getter
        @Setter
        public static class Consumer {
            private String groupId;
            private String autoOffsetReset;
            private Properties properties;
            private String keyDeserializer;
            private String valueDeserializer;

            @Getter
            @Setter
            public static class Properties {
                private SpringJson spring;

                @Getter
                @Setter
                public static class SpringJson {
                    private Trusted trusted;

                    @Getter
                    @Setter
                    public static class Trusted {
                        private List<String> packages;
                    }
                }
            }
        }

        @Getter
        @Setter
        public static class Producer {
            private String keySerializer;
            private String valueSerializer;
        }
    }

    @Getter
    @Setter
    public static class Logging {
        private String level;
    }
}