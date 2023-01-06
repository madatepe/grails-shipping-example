package ma.shipping.api

class Driver {
    Integer id
    String name
    String mobile
    String tcIdentityNumber

    static constraints = {
        name nullable: true
        mobile nullable: true
        tcIdentityNumber nullable: true
    }

    static mapping = {
        version false
    }
}
