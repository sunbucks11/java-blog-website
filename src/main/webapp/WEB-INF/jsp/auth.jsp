<%@include file="../layout/taglib.jsp"%>
<h2 id="section">
  Second Step Verification Required
</h2>

<div id="content">
  <h3 class="id"><span>Please enter the verification code here</span></h3>
    
  <div class="container">

    <c:choose>
        <c:when test="${initAuth}">
          <%-- <c:url var="formUrl" value="/admin/auth/init" /> --%>
          <c:url var="formUrl" value="/TwoFactorAuthController/init" />
        </c:when>
        <c:otherwise>
          <%-- <c:url var="formUrl" value="/admin/auth/verify" /> --%>
          <c:url var="formUrl" value="/TwoFactorAuthController/verify" />
        </c:otherwise>
    </c:choose>

    <form:form method="POST" action="${formUrl}" autocomplete="off" modelAttribute="formBean">

    <c:if test="${initAuth}">
        <h3 class="legend">You can either scan or enter the secret key into you google auth app</h3>

        <form:errors path="secretKey" cssClass="errorrow" />
        <div class="field left">
          <form:label class="title" path="secretKey">${secretKey}</form:label>
          <form:label class="title" path="barCodeUrl"><img SRC="${barCodeUrl}" width="100" height="100"></form:label>
          <form:input class="hidden" path="secretKey" value="${secretKey}" />
        </div>
      </c:if>

      <h3 class="legend">Please enter the verification code here once you setup the account</h3>
      
      <form:errors path="verificationCode" cssClass="errorrow" />
      <div class="field">
        <form:input class="verificationCode" path="verificationCode" />
      </div>
      
      <div class="rule"><hr /></div>

      <div class="submitrow">
        <input value="Login" type="submit">
      </div>
    </form:form>
    ${message}
  </div>
</div>
