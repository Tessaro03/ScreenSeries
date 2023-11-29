package screenmath.service;

public interface IcoverteDados {
    
    <T> T obterDados(String json, Class<T> classe);
}
