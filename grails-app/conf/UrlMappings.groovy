class UrlMappings {

	static mappings = {
        "/"(view:"/index")
        "500"(view:'/error')
        "/api/driver/$id?" (controller: "driverApi"){ action = [GET:"show", POST: "save"] }
        "/api/vehicle/$id?" (controller: "vehicleApi"){ action = [GET:"show", POST: "save"] }
        "/api/company/$id?" (controller: "companyApi"){ action = [GET:"show", POST: "save"] }
        "/api/shipping/$id?" (controller: "shippingApi"){ action = [GET:"show", POST: "save"] }
        "/api/invoice/$id?" (controller: "invoiceApi"){ action = [GET:"show", POST: "save"] }
        "/api/bulkInvoicingShippings/$id?" (controller: "invoiceApi"){ action = [POST: "bulkInvoicingShippings"] }
        "/api/collection/$id?" (controller: "collectionApi"){ action = [GET:"show", POST: "save"] }
        "/api/getCompanyCollections/$id?" (controller: "collectionApi"){ action = [GET:"getCompanyCollections"] }
        "/api/companyShippings" (controller: "reportApi"){ action = [POST:"companyShippings"] }
        "/api/vehicleShippings" (controller: "reportApi"){ action = [POST:"vehicleShippings"] }
        "/api/companyReportGeneralInformations" (controller: "reportApi"){ action = [GET:"companyReportGeneralInformations"] }
        "/api/vehicleReportGeneralInformations" (controller: "reportApi"){ action = [GET:"vehicleReportGeneralInformations"] }
        "/api/myReports" (controller: "reportApi"){ action = [GET:"myReports"] }
        "/api/lookup/$domain?/$refId?" (controller: "lookUpApi"){ action = [GET:"show"] }
//        "/api/companyReports/$id?" (controller: "invoiceApi"){ action = [GET:"show", POST: "save"] }
	}
}
