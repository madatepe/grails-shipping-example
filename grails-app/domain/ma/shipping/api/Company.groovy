package ma.shipping.api

class Company {
    Integer id
    String name
    String city
    String address
    String email
    String phone
    String mobile
    String representative
    String taxOffice
    Integer taxNumber
    Double credit = 0

    static constraints = {
        address nullable: true
        email nullable: true
        phone nullable: true
        mobile nullable: true
        representative nullable: true
        taxOffice nullable: true
        taxNumber nullable: true
    }

    static mapping = {
        version false
    }
}
