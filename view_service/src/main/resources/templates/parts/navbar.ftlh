<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Рассылатор</a>
    <button class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/emails">Адреса</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/messages">Письма</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mailing">Рассылка</a>
            </li>
<#--            <#if user??>-->
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="/user/profile">Профиль</a>-->
<#--                </li>-->
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="/user/messages/${currentUserId}">Ваши сообщения</a>-->
<#--                </li>-->
<#--            </#if>-->
<#--            <#if isAdmin>-->
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="/user">Пользователи</a>-->
<#--                </li>-->
<#--            </#if>-->
        </ul>
<#--        <div class="navbar-text mr-3"><#if user??>${name}<#else>Пожалуйста, авторизируйтесь</#if></div>-->
<#--        <@l.logout/>-->
    </div>
</nav>