package io.training.catalyte.sportsapparel.secrets;

/**
 * Storing a secret string necessary for the purpose of decoding a token for it's information.
 */
public abstract class Secret {

    private static final String SECRET = "MySecret";

    public static String getSECRET() {
        return SECRET;
    }
}
