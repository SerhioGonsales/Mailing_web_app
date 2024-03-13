<#import "parts/common.ftlh" as c>

<@c.page>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
            vertical-align: top;
            width: 100px;
            height: 35px;
        }

        th {
            background-color: #f2f2f2;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }
    </style>

    <table>
        <tr>
            <th>Темы</th>
            <th>Тексты</th>
            <th>Файлы</th>
        </tr>
        <tr>
            <td>
                <ul>
                    <#if subjects?has_content>
                        <#list subjects as subject>
                            <li>${subject.subject}</li>
                        </#list>
                    <#else>
                        <li>Обязательно добавьте темы</li>
                    </#if>
                </ul>
            </td>
            <td>
                <ul>
                    <#if texts?has_content>
                        <#list texts as text>
                            <li>${text.fileName}</li>
                        </#list>
                    <#else>
                        <li>Обязательно добавьте тексты</li>
                    </#if>
                </ul>
            </td>
            <td>
                <ul>
                    <#if files?has_content>
                        <#list files as file>
                            <li>${file.fileName}</li>
                        </#list>
                    <#else>
                        <li>Файлы можно не добавлять</li>
                    </#if>
                </ul>
            </td>
        </tr>
        <tr>
            <td style="border: #f2f2f2">
                <a href="deleteAllSubjects" style="color: red">удалить все темы</a>
            </td>
            <td style="border: #f2f2f2">
                <a href="deleteAllTexts" style="color: red">удалить все тексты</a>
            </td>
            <td style="border: #f2f2f2">
                <a href="deleteAllFiles" style="color: red">удалить все файлы</a>
            </td>
        </tr>
    </table>
    <br>
    <br>
    <p><b>Ниже можно добавить темы, тексты и файлы в базу</b></p>
    <form method="post" action="/addSubject">
        <div>
            <input class="form-control mt-2 mb-2 w-50"
                   type="text"
                   name="subject"
                   placeholder="Новая тема"/>
        </div>
        <div>
            <button type="submit"
                    class="btn btn-primary mt-2">Добавить тему</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
    <br>
    <#if emptyAttachedHTML>
        <b>!Проверьте выбранный файл, он должен быть в формате html!</b>
    </#if>
    <form enctype="multipart/form-data" method="post" action="/addText">
        <div>
            <label for="file">Загрузите файл с текстом письма в формате html</label>
        </div>
        <div>
            <input type="file" name="attachedHTML"/>
        </div>
        <div>
            <button type="submit"
                    class="btn btn-primary mt-2">Добавить текст</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
    <br>
    <form enctype="multipart/form-data" method="post" action="/addFile">
        <div>
            <label for="file">Добавьте файл (коммерческое или презентацию)</label>
        </div>
        <div>
            <input type="file" name="attachedFile"/>
        </div>
        <div>
            <button type="submit"
                    class="btn btn-primary mt-2">Добавить файл</button>
<#--            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        </div>
    </form>
    <br>
    <br>
</@c.page>