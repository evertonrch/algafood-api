package com.algaworks.algafood.service;

public class ServidorSmtp {

    private String host;
    private int port;
    private String user;

    public ServidorSmtp(String host, int port, String user) {
        this.host = host;
        this.port = port;
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ServidorSmtp{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                '}';
    }
}
