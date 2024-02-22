<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
String s=request.getParameter("value");
switch(s){
case 1:
   purchase.addPurchase();
   break;
case 2:
   invoice.createInvoice();
   break;
case 3:
   out.println("Thank you");
   break;
default:
   out.println("sorry!invalid entry enter a valid entry ");
    }
%>
</body>
</html>