package progi.megatron.service;


import progi.megatron.model.SecureToken;

public interface SecureTokenService2 {

    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken token);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}
