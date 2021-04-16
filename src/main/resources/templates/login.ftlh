<!-- -->
<!-- Назначаем шаблон повторяющегося кода, назначаем метку -->
<#import "Parts/common.ftlh" as c>
<#import "Parts/login.ftlh" as l>

<@c.page>
    ${message?ifExists}
    <@l.login "/login" false/>
</@c.page>