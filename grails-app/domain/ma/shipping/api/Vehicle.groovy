package ma.shipping.api

class Vehicle {
    Integer id
    String licensePlate
    String licensePlate2
    String type
    String owner
    String tcIdentityNumber
    String address
    String mobile
    String description
    Double debt = 0

    static constraints = {
        licensePlate2 nullable: true
        description nullable: true
        tcIdentityNumber nullable: true
        address nullable: true
    }

    static hasMany = [drivers: Driver]

    static mapping = {
        version false
        drivers cascade:'all-delete-orphan' // ne işe yaradığını bilmiyorum
    }
}
