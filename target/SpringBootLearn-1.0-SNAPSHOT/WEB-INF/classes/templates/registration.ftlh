<!-- -->
<!-- Назначаем шаблон повторяющегося кода, назначаем метку -->
<#import "Parts/common.ftlh" as c>
<#import "Parts/login.ftlh" as l>

<@c.page>
    Add new user
    <div>
        ${message?ifExists}  <!-- model.put("message", "User exists!");  -->
    </div>
    <!-- метка импорта кода @l, имя куска кода login, указываем значение переменной, которая используется в коде -->
    <@l.login "/registration" true />
</@c.page>