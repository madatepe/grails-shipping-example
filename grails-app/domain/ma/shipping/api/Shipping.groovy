package ma.shipping.api

class Shipping {
    Integer id
    String startLocation
    String endLocation
    String tonnage
    Double creditBaseAmount
    Double creditTaxAmount
    Double creditTotalAmount
    Double creditKilogram
    Double creditUnitPrice
    Date dateCreated
    Date date
    Company company
    Vehicle vehicle
    Driver driver
    Double debtBaseAmount
    Double debtTaxAmount
    Double debtTotalAmount
    Double debtAdvancePayment
    Double debtFuelPayment
    Double debtBalance

    def beforeInsert() {
        this.dateCreated = new Date()
    }

    static belongsTo = [Vehicle, Company, Driver]

    static constraints = {
        company nullable: true
        vehicle nullable: true
        driver nullable: true
        date nullable: true
        debtAdvancePayment nullable: true
        debtFuelPayment nullable: true
        debtBalance nullable: true
        debtBaseAmount nullable: true
        debtTaxAmount nullable: true
        debtTotalAmount nullable: true
        creditBaseAmount nullable: true
        creditTaxAmount nullable: true
        creditTotalAmount nullable: true
        creditKilogram nullable: true
        creditUnitPrice nullable: true
    }

    static hasMany = [invoices: Invoice]

    static mapping = {
        version false
        invoices cascade:'all-delete-orphan' // ne işe yaradığını bilmiyorum
    }
}
