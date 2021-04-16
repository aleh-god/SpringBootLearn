<!-- -->
<!-- Назначаем шаблон повторяющегося кода, назначаем метку -->
<#import "Parts/common.ftlh" as c>
<#import "Parts/login.ftlh" as l>

<@c.page>
    <div class="mb-1">Add new user</div>
    ${message?ifExists}
    <@l.login "/registration" true />
</@c.page>