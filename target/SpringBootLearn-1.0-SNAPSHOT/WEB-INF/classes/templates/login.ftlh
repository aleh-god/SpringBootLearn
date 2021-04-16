<!-- -->
<!-- Назначаем шаблон повторяющегося кода, назначаем метку -->
<#import "Parts/common.ftlh" as c>
<#import "Parts/login.ftlh" as l>

<@c.page>
    <!-- метка импорта кода @l, имя куска кода login, указываем значение переменной, которая используется в коде -->
    <@l.login "/login" false />
</@c.page>