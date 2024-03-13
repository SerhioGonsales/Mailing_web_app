<#import "parts/common.ftlh" as c>

<@c.page>
    <#if emailsCount??>
        <div>Новых адресов загружено: ${emailsCount} </div>
        <div>Из них уникальных: ${emailsUniqCount} </div>
        <br>
    </#if>
    <div> Всего в базе: ${emailsInBase} </div>
    <div>Адреса в работе: ${emailsNotSent} </div>
    <div> Отработанные адреса: ${emailsIsSent} </div>
    <form method="get">
        <div>
            <button type="submit" formaction="/resetSentStatus"
                    class="btn btn-outline-danger btn-sm mt-2">Сбросить отработанные адреса</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
        <div>
            <button type="submit"
                    formaction="/deleteAllEmails"
                    class="btn btn-outline-danger btn-sm mt-2">Очистить всю базу</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
    <br>
    <br>
    <form action="/uploadXSLX" method="POST" enctype="multipart/form-data">
        <div>
            <div>
                <label for="file">Загрузите файл (*.XSLX) с адресами для добавления в базу</label>
                <div>
                    <i>Адреса фильтруются на дубликаты,
                        а также на корректность написания email в формате -> *@*.*</i>
                </div>
                <br>
            </div>
            <div>
                <input type="file" name="attachedXSLX"><br>
            </div>
            <div>
                <input type="submit"
                       class="btn btn-primary mt-2" value="Добавить адреса в базу">
<#--                <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            </div>
            <#if error??>
                <div>Какая-то проблема с парсингом XSLS файла, повторите загрузку файла</div>
                <br>
            </#if>
        </div>
    </form>
</@c.page>