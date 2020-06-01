<%@page contentType="text/html" isErrorPage="true"%>
<%@page pageEncoding="UTF-8"%> 
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="系統錯誤"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <script>
        var s1 = "block";
        var s2 = "width:80%;display:blocked;font-size:60%;color:blue";
        function show_details() {
            var d = document.getElementById("details");
            try {
                d.style.setAttribute("display", s1);
                if (s1 == "none") {
                    s1 = "block";
                } else {
                    s1 = "none";
                }
            } catch (err) {
                d.setAttribute("style", (s2 == null ? "width:80%;display:none;" : s2));
                if (s2 == null) {
                    s2 = "width:80%;display:blocked;font-size:60%;color:blue";
                } else {
                    s2 = null;
                }
            }
        }
    </script>
    <p style='font-size:100%'>執行<span style='color:darkred'><%= request.getAttribute("javax.servlet.error.request_uri")%></span>時發生下列錯誤：<br/>
        <% if (exception != null) {
                out.println(exception);%>
        <a href="javascript:show_details()">details...</a><br/>
        <span id='details' style="width:60%;display:none;color:blue">
            <%
                exception.printStackTrace(new java.io.PrintWriter(out));
            %>
        </span>
        <% } else {
            Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
            if (status != null) {
        %>            
        <div>
            <img style="width:50%" src="<%= request.getContextPath()%>/images/<%=status%>.png" alt="<%= status%>">
        </div>
        <%   }
        }%>        
</p>
</div>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />