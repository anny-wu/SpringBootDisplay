<#macro select id values selected>
    <select id="${id}" name="${id}">
        <#list values?keys as key>
            <#if selected == key>
                <option value="${key}" selected>${values[key]}</option>
            <#else>
            <option value="${key}">${values[key]}</option>
            </#if>
        </#list>
    </select>
</#macro>
