package ma.shipping.api

import grails.transaction.Transactional
import ma.shipping.api.Invoice
import ma.shipping.api.Shipping

import java.text.SimpleDateFormat

@Transactional
class InvoiceApiService {

    def show(Map parameters) {
        def result

        if (parameters.id) {
            Invoice invoice = Invoice.read(parameters.id)

            result = invoice
        } else {
            List data = Invoice.list()

            result = [data: data]
        }

        return result
    }

    def save(Map parameters) {
        Invoice invoice = Invoice.get(parameters.id?.toInteger())

        if (!invoice) {
            invoice = new Invoice();
        }

        invoice.properties = parameters
        invoice.save(flush: true)

        return [data: invoice]
    }

    def bulkInvoicingShippings(Map parameters) {
        parameters.date = new SimpleDateFormat("yyyy-MM-dd").parse(parameters.date)

        parameters.shippingIds.each { shippingId ->
            Shipping shipping = Shipping.get(shippingId)
            Invoice invoice = new Invoice(
                    number: parameters.companyInvoiceNumber,
                    date: parameters.date,
                    type: true,
                    baseAmount: shipping.creditTotalAmount / 1.18,
                    taxAmount: 18,
                    totalAmount: shipping.creditTotalAmount,
                    company: shipping.company,
            )

            invoice.save(flush: true, failOnError: true);

            if (invoice) shipping.addToInvoices(invoice)
            shipping.save(flush: true)
        }

        return [data: [success: true]]
    }
}
