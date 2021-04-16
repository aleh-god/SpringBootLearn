<!-- Универсальный шаблон повторяющегося кода html-view -->
<!-- Назначаем кусок кода, даем имя login, указываем имя переменной с данными path -->
<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">User name</label>
            <input type="text" class="form-control" id="username" placeholder="User name">
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" placeholder="Password">
        </div>

        <div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <#if !isRegisterForm>
            <a href="/registration">Create account</a>
            </#if>
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>