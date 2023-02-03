<div class="display">
<h1>User Guide for the xymedia Programme Evaluation Service</h1>

<p>xymedia Evaluation service is split into two areas.  These are: 'Programme Evaluation', 
allowing press coverage of all programmes to be assessed and the more traditional 
'Corporate Evaluation' concentrating on key news and Corporate messages.</p>
<p>This guide covers the 'Programme Evaluation' service. The guide for the 'Corporate Evaluation' can be found <a href="/action/welcome?page=evalhelp">here</a>.</p>

<h2>1. 'Programme Evaluation'</h2>

<p>This allows press coverage of all broadcast programmes to be evaluated and can be accessed 
by clicking on 'Programme Evaluation'.</p>

<h3>Compiling a Report</h3>

<p>Individual reports can be created by using the 'Report Generation Wizard'.  This is found 
at the top right hand corner of the screen.</p>

<p>A report is compiled as follows: </p>


<h4>Step 1 - Article Filter.</h4>

<h5>Publication Date:</h5>

<p>'Any' will include all coverage evaluated to date</p>

<p>or</p>

<p>Particular dates for coverage to be evaluated can be specified by choosing the 'between dates' function</p>

<h5>Entry Date:</h5>

<p>As above but for the date articles were entered onto the xymedia system.</p>

<h5>Publication Type</h5>

<p>'One of' allows different types of publication to be chosen such as regionals or trade magazines.  
'Any' ensures coverage from all publications is evaluated.</p>

<h5>Section Type</h5>

<p>'Any' allows all sections of newspapers to be evaluated.  Alternatively particular sections can be 
chosen such as Business, Main or Sport.</p>

<h5>Article Type</h5>

<p>'One of' restricts coverage to particular types such as a preview article or feature comment. 'Any' ensures all coverage is evaluated.</p>

<h5>Publication <a href="#search">*</a></h5>

<p>'Any' covers all publications or 'One of' allows coverage from one or more titles to be evaluated.</p>

<h5>Journalist <a href="#search">*</a></h5>

<p>'Any' covers all journalists or 'One of' allows coverage from one or more journalists to be evaluated.</p>

<p>All selections are cumulative so if you select

<ul>
	<li>Publication Type = National</li>
</ul>
and
<ul>
	<li>Section Type = Media</li>
</ul>

the final report will be based on articles published in the Media sections of national newspapers.</p>

<p>If you select

<ul>
	<li>Publication = The Sun</li>
</ul>
and
<ul>
	<li>Journalist = Joe Brown</li>
</ul>

only articles written by Joe Brown and published in The Sun will appear.</p>

<a name="search"></a>
<p>* The Publication, Journalist and Programme selection are selected by typing a search phrase in the 
Search box and pressing the "Search" button.  This returns a list of matching items in the "Found" section 
from which any number of items from this list can be selected by pressing "Select".

<h4>Step 2 - Programme Filter</h4>

<p>This page allows the programmes you wish to include in the report to be selected. This page is only visible 
in the "Programme Evaluation" service.</p>

<h5>Broadcaster</h5>

<p>'Any' covers all Broadcasters or 'One of' allows coverage from one or more Broadcaster to be evaluated.</p>

<h5>Broadcast Type</h5>

<p>This allows programmes broadcast on a particular broadcast type (Terrestrial, Freeview, Cable etc) to be evaluated.</p>

<h5>Channel</h5>

<p>This allows programmes from particular channels to be compared and evaluated.</p>

<h5>Producer</h5>

<p>Programmes can be evaluated by particular production companies if required.</p>

<h5>Genre</h5>

<p>Programmes from a particular Genre (Drama, Soap, Documentary etc) can be evaluated or compared.</p>

<h5>Programme <a href="#search">*</a></h5>

<p>You may select individual programmes to evaluate.</p>

<p>Again these functions are cumulative. If you select:
<ul>
	<li>Genre = Drama</li>
</ul>
and
<ul>
	<li>Channel = BBC 1</li>
</ul>

your report will only contain Dramas broadcast on BBC1.</p>

<h4>Step 3 - Grouping</h4>

<p>The functions on this page allow the evaluation data/articles specified to be aggregated or grouped together.</p>

<p>The number of the available fields to group by can be selected. The more fields chosen will create a larger number of rows in a table. If only 'Broadcaster' is selected only one row per broadcast company will be generated as follows:

<table>
	<thead>
		<tr><th>Broadcaster</th><th>Count</th></tr>
	</thead>
	<tbody>
		<tr class="odd"><td>BBC</td><td>54</td></tr>
		<tr class="even"><td>Channel 4</td><td>34</td></tr>
		<tr class="odd"><td>Five</td><td>13</td></tr>
	</tbody>
</table>
</p>

<p>If Programme and Publication are selected one row per Programme/Publication combination will be generated and the following will be created:

<table>
	<thead>
		<tr><th>Programme</th><th>Publication</th><th>Count</th></tr>
	</thead>
	<tbody>
		<tr class="odd"><td>Big Brother</td><td>The Sun</td><td>3</td></tr>
		<tr class="even"><td>Big Brother</td><td>Daily Star</td><td>2</td></tr>
		<tr class="odd"><td>Big Brother</td><td>The Times</td><td>5</td></tr>
		<tr class="even"><td>The Farm</td><td>The Sun</td><td>1</td></tr>
		<tr class="odd"><td>The Farm</td><td>The Times</td><td>12</td></tr>
	</tbody>
</table>
</p>

<h4>Step 4 - Table Data</h4>

<p>Functions on this page allow the information that appears in the bottom table of the report to be selected.</p>

<p>If a grouping on the previous page has been selected only those fields will be available here to be selected (i.e if you're grouping by channel you can't select programme as a column in your table as all a channel's programmes will be aggregated into one row).</p>

<p>The PGS index quantifies the impact of mentions on the general public in order to make comparisons between programmes, publications or any other criteria. The numeric score is based on the tonality and size of the article, its position in the paper and the circulation of the publication.</p>

<p>The number of rows appearing in the table can be limited by using the last set of controls. Select the number of records required to appear in the table and the evaluation datum used to sort the results before picking the top x rows.</p>

<p>For example by selecting return the top 5 results ordered by total area, the 5 rows with the largest total area will be returned. If there are multiple records with the same value these will also be returned:

<table>
	<thead>
		<tr><th>Publication</th><th>Total Area</th></tr>
	</thead>
	<tbody>
		<tr class="odd"><td>The Sun</td><td>564</td></tr>
		<tr class="even"><td>The Star</td><td>534</td></tr>
		<tr class="odd"><td>The Mirror</td><td>234</td></tr>
		<tr class="even"><td>Daily Mail</td><td>201</td></tr>
		<tr class="odd"><td>Daily Telegraph</td><td>143</td></tr>
		<tr class="even"><td>The Times</td><td>143</td></tr>
		<tr><td colspan="2">Records below are not displayed</td></tr>
		<tr><td>The Metro</td><td>132</td></tr>
		<tr><td>Evening Standard</td><td>87</td></tr>
	</tbody>
</table>
In this example the first six results are displayed because the 5th and 6th results have the same Total Area.</p>

<p>All other results can be aggregated into a single row labelled others if required. If this was selected the above table would display as follows.
<table>
	<thead>
		<tr><th>Publication</th><th>Total Area</th></tr>
	</thead>
	<tbody>
		<tr class="odd"><td>The Sun</td><td>564</td></tr>
		<tr class="even"><td>The Star</td><td>534</td></tr>
		<tr class="odd"><td>The Mirror</td><td>234</td></tr>
		<tr class="even"><td>Daily Mail</td><td>201</td></tr>
		<tr class="odd"><td>Daily Telegraph</td><td>143</td></tr>
		<tr class="even"><td>The Times</td><td>143</td></tr>
		<tr class="odd"><td>Others</td><td>219</td></tr>
	</tbody>
</table>
</p>

<h4>Stage 5 - Chart Data</h4>

<p>This page allows the information that appears in the chart at the top of the page to be selected.</p>

<h5>Chart Style</h5>

<p>This allows the style of the report to be selected from the following:
<dl>
	<dt>Bar Chart</dt>
	<dd>Category axis runs up the side, value axis along the bottom of the chart. 
	If you select a series axis each category will have one bar per series.</dd>
	<dt>Column Chart</dt>
	<dd>As the bar chart but with the axis reversed.</dd>
	<dt>Pie Chart</dt>
	<dd>Series axis is not used for this type of chart.</dd>
	<dt>Stacked Bar Chart</dt>
	<dd>Similar to a bar chart but each category has only one bar with each series stacked one on top of the next.</dd>
	<dt>Stacked Column Chart</dt>
	<dd>As above but with axis reversed.</dd>
</dl>

<p>A title and subtitle for the chart and labels for the axes may be entered if required. If these are left blank then default labels will be used.</p>

<p>As in the previous page, a range of fields can be selected to appear as the value on the chart. 
Unlike the previous page only one field will appear on the chart.</p>

<p>Again as the previous page you can limit the number of categories that appear on the chart.</p>

<p>Once this final step has been completed the report can be executed. The next page will display the chart and the data table of the finished report.</p>

<p>Changes to the report's criteria can be made by using the Previous button or if satisfied with the report pressing 'save' will save the report.</p>

</div>