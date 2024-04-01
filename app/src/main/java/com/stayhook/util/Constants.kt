package com.stayhook.util

import com.stayhook.model.response.UserResponse
import com.stayhook.preference.PreferenceHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Constants {

    object PreferenceConstant : KoinComponent{
        private val mPref: PreferenceHelper by inject()
        const val PREFERENCE_NAME = "StayHookApplication"
        const val IS_LOGIN = "IS_LOGIN"
        const val TOKEN = "TOKEN"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val AUTHORIZATION = "Authorization"
        const val USER_DETAIL = "USER_DETAIL"

        const val IS_BACK_PRESS_TRUE = "IS_BACK_PRESS_TRUE"
        const val API_TIMEOUT = 60000L
        fun updateUserDetail(response: UserResponse) {
            val updateResponse = UserResponse().apply {
                id = response.id
                firstName = response.firstName
                mobileNo = response.mobileNo
                profile = response.profile
                gender = response.gender
                fullName = response.fullName
                token = response.token
            }
          //  mPref.setUserDetail(updateResponse)
        }
    }

    class NetworkConstant{
        companion object{
            const val API_SUCCESS=200
            const val API_AUTH_ERROR=403
            const val BAD_REQUEST = 401
            const val API_TIME_OUT = 60000
            const val NO_INTERNET_AVAILABLE = "Oops! No Internet"
            const val CONNECTION_LOST = "Oops! Connection lost! "
            const val API_INTERNAL_SERVER_ERROR=500
            const val BASE_URL = "https://stayhook.com/api/"
            const val loginApi = "userLogin"
            const val registerApi = "userRegister"
            const val homeApi = "homePage"
            const val getPropertyApi = "getProperty"
            const val getPropertyDetailApi = "getPropertyDetails"
            const val getPropertyRoomApi = "getPropertyRoom"
            const val getPropertyBedApi = "getRoomBed"
            const val tokenCollected = "tokenCollected"
            const val getTokenCollected = "getTokenCollected"
            const val getMyBooking = "getMyBooking"
            const val getScheduledToken = "getScheduleToken"
            const val requestMoveout = "requestMoveout"
            const val getScheduledDetailToken = "getScheduleTokenDetails"
            const val scheduleVisit = "scheduleVisit"
            const val myPayments = "myPayment"

            const val updateApi = "updateProfile"
            const val updateKYCApi = "updateKyc"
            const val myProfileApi = "myProfile"
            const val getStateApi = "getState"
            const val getCityApi = "getCity"

            const val addReview = "addReview"
            const val addRemoveFavorite = "addRemoveFavorite"

            const val getIssueType = "getIssueType"
            const val getIssueSubType = "getIssueSubType"

            const val createTicket = "createTicket"
            const val getTicketProperty = "myProperty"
            const val myTicket = "getTicket"

            const val getTicketDetails = "getTicketDetails"
            const val addTicketMeeting = "addTicketMeeting"
            const val addTicketComment = "addTicketComment"

        }
    }
    class DefaultConstants{
        companion object{
            const val BUNDLE="bundle"
            const val MODEL_DETAIL="MODEL_DETAIL"
            const val SELECT_PROPERTY_ID="property_id"
            const val SELECT_PROPERTY_ROOM_ID="property_room_id"
            const val SELECT_PROPERTY_ROOM_BED_ID="property_room_bed_id"
            const val STRING = "title"
            const val ROOM_TYPE = "title"

        }
    }


    class StringContents{
        companion object{
            const val PRIVACY_POLICY="PRIVACY_POLICY"
            const val PRIVACY_POLICY_DESC="Welcome to Stayhook, a tech-based living startup that provides co-living\n" +
                    "accommodations and property management services to landlords. Stayhook is a\n" +
                    "registered trademark of Tio Tech Private Limited. This Privacy Policy outlines how we\n" +
                    "collect, use, disclose, and protect your personal information when you use our\n" +
                    "services.\n" +
                    "1. Information We Collect:\n" +
                    "We collect various types of information to provide and improve our services. This\n" +
                    "includes:\n" +
                    "1.1. Personal Information: We collect information that identifies you personally, such\n" +
                    "as your name, contact details, and payment information.\n" +
                    "1.2. Usage Information: We gather data about how you use our app, including your\n" +
                    "interactions, preferences, and activities.\n" +
                    "1.3. Device Information: We may collect information about your device, such as its\n" +
                    "type, model, operating system, and unique identifiers.\n" +
                    "1.4. Location Information: With your consent, we may collect precise location data to\n" +
                    "enhance the services we provide, such as property listings and navigation.\n" +
                    "2. How We Use Your Information:\n" +
                    "2.1. Service Provision: We use your information to provide and improve our co-living\n" +
                    "accommodation and property management services, including features like property\n" +
                    "listing, complaint handling, move-in, move-out, and payment processing.\n" +
                    "2.2. Communication: We may use your contact information to communicate with you\n" +
                    "about our services, updates, and relevant information.\n" +
                    "2.3. Analytics: We analyze user interactions to enhance the functionality and\n" +
                    "performance of our app.\n" +
                    "3. Information Sharing:\n" +
                    "3.1. With Your Consent: We may share your information with third parties when you\n" +
                    "give us explicit consent to do so.\n" +
                    "3.2. Service Providers: We may share information with trusted third-party service\n" +
                    "providers who assist us in delivering and improving our services.\n" +
                    "3.3. Legal Compliance: We may disclose information in response to a legal obligation\n" +
                    "or when required by law.\n" +
                    "4. Security:\n" +
                    "We employ industry-standard security measures to protect your information from\n" +
                    "unauthorized access, disclosure, alteration, and destruction.\n" +
                    "5. Your Choices:\n" +
                    "You have the right to access, correct, or delete your personal information. You can\n" +
                    "manage your preferences within the app settings.\n" +
                    "6. Updates to this Privacy Policy:\n" +
                    "We may update this Privacy Policy periodically to reflect changes in our practices. We\n" +
                    "will notify you of any significant updates through the app or other communication\n" +
                    "channels.\n" +
                    "7. Contact Us:\n" +
                    "If you have questions or concerns about this Privacy Policy, please contact us at\n" +
                    "support@stayhook.com.\n" +
                    "By using Stayhook's services, you agree to the terms outlined in this Privacy Policy.\n" +
                    "Please review this policy regularly for updates.\n" +
                    "Thank you for choosing Stayhook!\n" +
                    "Tio Tech Private Limited\n"



            const val DISCLAIMER ="DISCLAIMER"
            const val DISCLAIMER_DESC ="Disclaimer:\n" +
                    "Stayhook is a registered trademark of Tio Tech Private Limited. Stayhook operates as a\n" +
                    "technology-driven living startup specializing in co-living accommodations and property\n" +
                    "management services for landlords. All services provided by Stayhook, including but not\n" +
                    "limited to property listing, complaint submissions, move-in and move-out processes, as\n" +
                    "well as payment transactions, are conducted exclusively through the Stayhook mobile\n" +
                    "application.\n" +
                    "By using the Stayhook app and availing our services, you acknowledge and agree that\n" +
                    "Tio Tech Private Limited, the parent company of Stayhook, shall not be held responsible\n" +
                    "for any inaccuracies, errors, or omissions in property listings, complaints, or any other\n" +
                    "content provided through the app. Stayhook strives to ensure the accuracy and reliability\n" +
                    "of information but does not guarantee the completeness or suitability of the content for\n" +
                    "any particular purpose.\n" +
                    "Stayhook reserves the right to modify, suspend, or discontinue any aspect of its services\n" +
                    "or the mobile application at any time. Tio Tech Private Limited shall not be liable for any\n" +
                    "direct, indirect, incidental, consequential, or exemplary damages arising from the use of\n" +
                    "the Stayhook app or its services.\n" +
                    "Landlords and users are encouraged to exercise due diligence and verify information\n" +
                    "independently. Stayhook does not endorse, guarantee, or assume responsibility for the\n" +
                    "accuracy or reliability of any information or content provided by landlords or users on\n" +
                    "the platform.\n" +
                    "By using Stayhook, you agree to comply with all applicable laws and regulations. Tio\n" +
                    "Tech Private Limited disclaims any liability for the actions or conduct of landlords,\n" +
                    "users, or third parties using the Stayhook platform.\n" +
                    "The information provided through the Stayhook app is for general informational\n" +
                    "purposes only and should not be considered as professional advice. Users are advised\n" +
                    "to seek appropriate legal, financial, or real estate advice as needed.\n" +
                    "Stayhook and Tio Tech Private Limited reserve the right to update or modify this\n" +
                    "disclaimer at any time without prior notice. Users are encouraged to review this\n" +
                    "disclaimer periodically for changes.\n" +
                    "For any inquiries or concerns, please contact Stayhook support at\n" +
                    "support@stayhook.com."



            const val REFUND_POLICY="REFUND POLICY"
            const val REFUND_POLICY_DESC="Refund Policy for Stayhook:\n" +
                    "Booking Token Amount:\n" +
                    "● Non-refundable upon cancellation.\n" +
                    "Cancellation Before License Start Date:\n" +
                    "● If LESSEE cancels before the license start date, the security deposit shall\n" +
                    "be entirely refunded.\n" +
                    "● Token amount shall not be refunded.\n" +
                    "● Refund depends on the date of cancellation with respect to the booking\n" +
                    "date (refer to Additional Cancellation Charges section).\n" +
                    "Non-payment of Remainder of Security Deposit:\n" +
                    "● If the LESSEE doesn't pay the remainder of the security deposit two days\n" +
                    "prior to the license start date, the booking shall be auto-cancelled.\n" +
                    "Re-booking or Refund after Auto Cancellation:\n" +
                    "● LESSEE can choose to re-book or initiate a refund within three days of\n" +
                    "cancellation.\n" +
                    "● After three days, the refund is initiated automatically if bank details are\n" +
                    "provided to the Agent.\n" +
                    "● Token amount shall be refunded completely, and the refund depends on\n" +
                    "the date of cancellation with respect to the booking date (refer to\n" +
                    "Additional Cancellation Charges section).\n" +
                    "On-boarding Charges:\n" +
                    "● No refund shall be made for on-boarding charges if cancellation is done\n" +
                    "before the move-in date.\n" +
                    "Note: Additional Cancellation Charges section should be referred to for specific details\n" +
                    "on refund calculations based on the date of cancellation.\n" +
                    "Stayhook, a registered trademark of Tio Tech Private Limited, operates as a tech-based\n" +
                    "living startup, providing co-living accommodations and property management services\n" +
                    "through its app. Services such as property listing, complaints, move-in, move-out, and\n" +
                    "payments are facilitated entirely through the app."


            const val CANCELLATION_POLICY="CANCELLATION POLICY"
            const val CANCELLATION_POLICY_DESC="Token Amount:\n" +
                    "● Non-refundable upon booking.\n" +
                    "Cancellation Before License Start Date:\n" +
                    "● If LESSEE cancels before the license start date, the security deposit will\n" +
                    "be entirely refunded.\n" +
                    "● Token amount shall not be refunded.\n" +
                    "● Additional Cancellation Charges may apply (refer to Additional\n" +
                    "Cancellation Charges section).\n" +
                    "Auto Cancellation:\n" +
                    "● If the LESSEE fails to pay the remainder of the security deposit two days\n" +
                    "prior to the license start date, the booking will be auto-cancelled.\n" +
                    "● No refund of on-boarding charges, token, or any collected amount in case\n" +
                    "of cancellation before the move-in date.\n" +
                    "No Cancellation After Move-In Date:\n" +
                    "● No cancellation allowed after the move-in date.\n" +
                    "Security Deposit Refund:\n" +
                    "● Refunded after Move-Out, with deductions for any outstanding amounts\n" +
                    "as per Stayhook policies.\n" +
                    "● Refund process within 15 bank working days from the day of move-out.\n" +
                    "Early Termination Charges:\n" +
                    "● If LESSEE decides to Move-Out before 9 months, one month's rent will be\n" +
                    "charged as early termination charges, provided a 60-day notice is given.\n" +
                    "● LESSEE must serve a 60-day notice period; any shortfall will be charged\n" +
                    "pro rata based on the shortfall in the number of days from the 10-day\n" +
                    "notice period.\n" +
                    "Rent Deduction for Inadequate Notice:\n" +
                    "● If LESSEE Moves-Out within 9 months without giving a 60-day notice,\n" +
                    "rent for one month shall be deducted.\n" +
                    "Deductions for Damages:\n" +
                    "● Utility payments, charges for damages, or any outstanding dues shall be\n" +
                    "deducted from the security deposit.\n" +
                    "Fixed Exit Charge:\n" +
                    "● A charge of INR 2000 shall be deducted as a fixed charge that every\n" +
                    "tenant must bear upon exit.\n" +
                    "Damages Payment:\n" +
                    "● LESSEE is responsible for paying actual costs for damages to the property or its\n" +
                    "belongings."


            const val ABOUT_US="ABOUT US"
            const val ABOUT_US_DESC="Welcome to Stayhook, a pioneering venture in the realm of tech-based living, proudly\n" +
                    "brought to you by Tio Tech Private Limited. Stayhook is a registered trademark that\n" +
                    "stands at the forefront of revolutionizing co-living accommodations and property\n" +
                    "management services.\n" +
                    "At Stayhook, we leverage cutting-edge technology to redefine your living experience.\n" +
                    "Our innovative app-driven platform seamlessly integrates various services, providing a\n" +
                    "hassle-free and efficient way for both landlords and tenants to manage their co-living\n" +
                    "spaces.\n" +
                    "Our Services:\n" +
                    "● Property Listing: Discover and explore a diverse range of co-living spaces\n" +
                    "through our user-friendly app.\n" +
                    "● Complaint Management: Report and address concerns with ease, ensuring a\n" +
                    "comfortable living environment.\n" +
                    "● Move-In/Move-Out: Streamline the entire moving process conveniently through\n" +
                    "our app.\n" +
                    "● Payment: Effortlessly handle rental payments and transactions securely within\n" +
                    "the digital realm.\n" +
                    "Booking Policies:\n" +
                    "● A non-refundable token amount secures your booking.\n" +
                    "● Refund of the security deposit is subject to cancellation timing, as outlined in\n" +
                    "our policies.\n" +
                    "● Failure to pay the remainder of the security deposit two days prior to the license\n" +
                    "start date results in automatic cancellation.\n" +
                    "Cancellation and Termination:\n" +
                    "● No refunds for on-boarding charges, tokens, or any collected amount for\n" +
                    "cancellations before the move-in date.\n" +
                    "● No cancellations allowed after the move-in date.\n" +
                    "● Early termination charges apply if the LESSEE decides to Move-Out before 9\n" +
                    "months, with a 60-day notice period.\n" +
                    "Move-Out and Security Deposit:\n" +
                    "● Security deposit refunded after Move-Out, with deductions for any outstanding\n" +
                    "amounts or damages.\n" +
                    "● INR 2000 fixed charge deducted from every tenant's security deposit upon exit.\n" +
                    "● LESSEE is responsible for actual damages to the property or its belongings.\n" +
                    "At Stayhook, we are committed to providing a seamless and tech-driven living\n" +
                    "experience. Your comfort and satisfaction are our top priorities. Join us on this\n" +
                    "journey, where technology meets lifestyle, and discover a new era of co-living with\n" +
                    "Stayhook!"




        }
    }
}