
<div class="form-container">
	<fieldset>
		<legend></legend>
		<#list variables as variable>
		<div>
			<label for="manager">${variable.name}</label>
			${variable.tag}
		</div>
		</#list>
	</fieldset>
</div>