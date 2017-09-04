<%@ page contentType="text/xml; charset=UTF-8" isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource driver="com.mysql.jdbc.Driver"
  url="jdbc:mysql://localhost/cheea?useUnicode=true&amp;characterEncoding=utf8"
  user="root" password="123"/>
<anychart>
  <charts>
    <chart>
      <chart_settings>
        <title>
        <text>排课系统</text>
        </title>
        <axes>
          <x_axis>
            <title>
              <text>排课成功数</text>
            </title>
          </x_axis>
          <y_axis>
            <title>
              <text>次数</text>
            </title>
            <labels>
              <format>{%Value}</format>
            </labels>
          </y_axis>
        </axes>
      </chart_settings>
      <data_plot_settings>
        <bar_series>
          <tooltip_settings enabled="true">
            <format><![CDATA[{%Name}{enabled:false} Volume: {%YValue}$]]> </format>
          </tooltip_settings>
        </bar_series>
      </data_plot_settings>
      <data>
        <series name="study">
          <sql:query var="q">
            select * from t_data
          </sql:query>
          <c:forEach var="item" items="${q.rows}">
            <point name="${item.name}" y="${item.number}"/>
          </c:forEach>
        </series>
      </data>

    </chart>
  </charts>
</anychart>