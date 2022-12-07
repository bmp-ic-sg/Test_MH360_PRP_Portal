<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="application" var="bundle" scope="session" />

<%@include file="header.jsp"%>

<c:if test="${sessionScope.pg_mobile_verified == true}">
    <c:redirect url="/register" />
</c:if>
<style>
    .uss-tel .iti__arrow{
        background-image: url('<fmt:message key="context.path" bundle="${bundle}" />/assets/images/caret-tel.svg');
    }
</style>

</head>
<body>
    <div class="layout" id="otp">
        <div class="d-flex layout-box" style="min-height:90vh;">
            <div class="col mx-auto layout-box-width">
                <div class="d-flex justify-content-center mb-2 mt-4 p-2 header-shadow">
                    <span class="m-0 fs-5 text-header-label color-black">Patient Registration</span>
                </div>
                <div class="card border-0 mt-4">
                    <div class="card-body">
                        <div class="text-center mb-4">
                            <span>Enter your mobile number to receive a one-time
                                passcode for validation</span>
                        </div>
                        <form @submit.prevent="handleSubmit" v-cloak>
                            <div class="group">
                                <div class="input-group uss-tel">
                                    <input v-model="phone" type="tel" inputmode="numeric" pattern="[0-9]*" class="form-control" id="phone" maxlength="19"
                                        :style="{borderLeft: '1px solid #DFE2E6', borderColor: borderColor, paddingRight: '1.2em'}"
                                        placeholder="Mobile Number"
                                        onkeypress="return isNumberKey(event)">
                                    <button type="reset" class="btn bg-transparent" style="margin-left: -40px">
                                        <span class="text-danger">&times;</span>
                                    </button>
                                </div>
                                <div class="text-small invalid-feedback text-left d-inline" v-cloak>{{
                                    errorMessage }}</div>
                                <div v-if="isAPIError" class="text-small invalid-feedback text-center d-block">Something
                                    went wrong. Please try again.</div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="btn-bottom-container">
            <button type="submit" class="btn parkway-button fw-bold parkway-button-full" @click="handleSubmit" :disabled="isLoading || !phone">
                <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status"
                    aria-hidden="true"></span> <span v-if="!isLoading">Send OTP SMS</span>
            </button>
        </div>
    </div>

    <%@include file="footer.jsp"%>
    <script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/intl-tel-input/intlTelInputSearch.min.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
    <script src="<fmt:message key="context.path" bundle="${bundle}" />/js/otp.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
    <script>
        function isNumberKey(evt) {
            var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode > 31 && (charCode < 48 || charCode > 57))
                return false;
            return true;
        }
        function filterFunction() {
        	var input, filter, ul, li, i, pre;
            input = document.getElementById("searchCountryinline");
            filter = input.value.toUpperCase();
            ul = document.getElementById("iti-0__country-listbox");
            li = ul.getElementsByTagName("li");
            for (i = 0; i < li.length; i++) {
                txtValue = li[i].textContent || li[i].innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    li[i].style.display = "";
                } else {
                    li[i].style.display = "none";
                }
            }
            for (pre = 0; pre < 3; pre++) {
            	if (input.value === "") {
            		li[pre].style.display = "";
            	} else {
            		li[pre].style.display = "none";
            	}
            }
        }
    </script>
</body>

</html>