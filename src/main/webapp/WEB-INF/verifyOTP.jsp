<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="application" var="bundle" scope="session" />

<link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/otpInput.css">
<%@include file="header.jsp"%>

<c:if test="${sessionScope.pg_mobile_no == null && sessionScope.pg_mobile_country_code == null}">
    <c:redirect url="/mobile/verify" />
</c:if>

<c:if test="${sessionScope.pg_mobile_verified == true}">
    <c:redirect url="/register" />
</c:if>

</head>
<body>
    <div class="layout" id="verifyOTP">
        <div class="d-flex layout-box">
            <div class="col mx-auto layout-box-width">
                <div class="d-flex justify-content-center mb-2 mt-4 p-2 header-shadow">
                    <span class="m-0 fs-5 text-header-label color-black">Patient Registration</span>
                </div>
                <div class="d-block uss-help-question">
                    <a @click="toggleHelp"> <img
                        src="<fmt:message key="context.path" bundle="${bundle}" />/assets/images/question.svg" alt="Help">
                    </a>
                </div>
                <div class="card border-0 mt-4">
                    <div class="card-body">
                        <div class="text-center mb-4">
                            <span>Please enter the 6 digits we sent to your mobile
                                number</span>
                        </div>
                        <form v-on:submit.prevent="handleSubmit" v-cloak autocomplete="off">
                            <div class="group">
                                <div id="OTPInput" class="d-flex justify-content-center uss-otp"></div>
                                <div v-if="isInvalidOTP" class="text-small invalid-feedback text-center d-block">The
                                    OTP you've entered is incorrect. Please try again.</div>
                            </div>
                            <div class="text-center">
                                <button type="button" class="btn fw-bold text-decoration-none"
                                    :style="{backgroundColor: 'transparent !important', padding: '0', color: '#32c3e0 !important', border: 'none !important'}"
                                    :disabled="isLoadingResend" @click="resendOTP">
                                    <span v-if="isLoadingResend" class="spinner-border text-info spinner-border-sm"
                                        role="status" aria-hidden="true"></span> Resend OTP
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="btn-bottom-container">
            <button type="submit" class="btn parkway-button fw-bold parkway-button-full" @click="handleSubmit" :disabled="isVerifying">
                <span v-if="isLoadingVerify" class="spinner-border spinner-border-sm" role="status"
                    aria-hidden="true"></span> <span v-if="!isLoadingVerify">Verify</span>
            </button>
        </div>
        <div class="uss-message-tips d-none" v-if="showHelp">
            <div class="uss-message-tips-backdrop" @click="toggleHelp"></div>
            <div class="uss-message-tips-container">
                <h2 class="fw-bold">OTP Help</h2>
                <span>Please contact our service hotline if you need to update your mobile number to receive the OTP.</span>
                <a href="https://wa.me/message/N3FTJUMWNK2PC1" target="_blank" class="btn parkway-button parkway-button-full mt-4 fw-bold">Call Service Hotline</a>
                <button class="btn parkway-button-full parkway-button-white fw-bold mt-2" @click="toggleHelp">Dismiss</button>
            </div>
        </div>
        <div class="toast-container position-absolute py-3 top-0 end-2vw">
            <div class="toast toast-success d-flex align-items-center text-white bg-success border-0 w-95vw" role="alert"
                aria-live="assertive" aria-atomic="true">
                <div class="toast-body">Succeed to re-send OTP. A New OTP will be sent to the mobile number if the current OTP has expired.</div>
                <button type="button" class="btn-close ms-auto me-2" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast toast-danger d-flex align-items-center text-white bg-danger border-0 w-95vw" role="alert"
                aria-live="assertive" aria-atomic="true">
                <div class="toast-body">Oops! Failed to re-send OTP. Please try again.</div>
                <button type="button" class="btn-close ms-auto me-2" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp"%>
    <script src="<fmt:message key="context.path" bundle="${bundle}" />/js/verifyOTP.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
    <script>
        function isNumberKey(evt) {
            var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode > 31 && (charCode < 48 || charCode > 57))
                return false;
            return true;
        }
    </script>
</body>

</html>