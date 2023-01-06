package ma.shipping.api

class Invoice {
    Integer id
    Integer number
    Boolean type /* type 0 = Vehicle, type 1 = Company  */
    Date date
    Double baseAmount
    Double taxAmount
    Double totalAmount
    Date dateCreated
    Vehicle vehicle
    Company company

    def beforeInsert() {
        this.dateCreated = new Date()
    }

    static constraints = {
        type nullable: true
        date nullable: true
        company nullable: true
        vehicle nullable: true
        dateCreated nullable: true
    }

    static belongsTo = [Vehicle, Company]

    static mapping = {
        version false
    }
}
