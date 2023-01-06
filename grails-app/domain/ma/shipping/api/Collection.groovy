package ma.shipping.api

class Collection {

    Integer id
    Date date
    Double amount
    Company company
    String description

    static constraints = {
        date nullable: true
    }

    static belongsTo = [Company]

    static mapping = {
        version false
    }
}
