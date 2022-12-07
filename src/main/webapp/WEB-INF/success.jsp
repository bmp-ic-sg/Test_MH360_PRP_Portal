<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="application" var="bundle" scope="session" />
<%@include file="header.jsp"%>

<c:if test="${sessionScope.pg_mobile_verified == null || sessionScope.pg_mobile_verified == false}">
    <c:redirect url="/" />
</c:if>

</head>
<body>
    <div class="layout" id="success">
        <div class="d-flex align-items-center layout-box" style="min-height:90vh;">
            <div class="col-sm-8 col-md-8 col-lg-4 mx-auto layout-box-width">
                <div class="text-center mt-5 mb-1">
                    <div class="logo">
                        <img src="../assets/images/digihealth.svg" alt="DigiHealth" class="img-fluid"/>
                    </div>
                </div>
                <div class="d-flex justify-content-center mb-4">
                    <span class="m-0 fs-5 text-header-label color-black">PATIENT REGISTRATION</span>
                </div>
                <div class="card card-shadow text-center">
                    <div class="card-body">
                        <div class="py-4">
                            <span class="iconify pb-3" data-inline="false" data-icon="bi:check-circle-fill"
                                style="font-size: 2.625rem; color: #52C41A;"></span>
                            <h5 class="fw-bold">Thank You</h5>
                            <p class="text-small color-black-50">Your account has been created.</p>
                        </div>
                        <div class="group d-grid">
                            <button type="submit" class="btn text-white fw-bold" @click="gotoLogin"
                                :disabled="isLoading">
                                <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status"
                                    aria-hidden="true"></span> <span v-if="!isLoading">Login</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp"%>
    <script src="<fmt:message key="context.path" bundle="${bundle}" />/js/success.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
</body>

</html>