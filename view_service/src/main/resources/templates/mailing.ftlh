<#import "parts/common.ftlh" as c>

<@c.page>
    <#if testMessageIsSend??>
        <div>
            <p>На вашу почту отправлено тестовое письмо, проверьте его.</p>
            <p>Если с ним все в порядке, то можете
                <a href="/mailing/startMailing">начинать рассылку.</a></p>
        </div>
    </#if>
    <#if mailingIsStart??>
        <p>Рассылка успешно началась</p>
    </#if>
    <div>
        <p>Программа будет отсылать по одному письму каждые 30 минут с 9:00 до 18:00.</p>
        <p>В каждом следующем письме комбинация "тема", "текст" и "вложенный файл" будут разными.</p>
    </div>
    <form method="post">
        <div>
            <button
                    type="submit" formaction="mailing/testMail"
                    class="btn btn-outline-primary btn-sm mt-2">Отправить тестовое письмо себе на почту</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
    <form method="post">
        <br>
        <div>
            <button
                    type="submit" formaction="/mailing/startMailing"
                    class="btn btn-outline-success btn-sm mt-2">Начать рассылку</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
</@c.page>