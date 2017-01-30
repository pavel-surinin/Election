var CountyDetailViewDistrictRowComponent = React.createClass({
	render: function(){
		return (
				<tr>
	              <td>
	                {this.props.name}
	              </td>
	              <td>
	                {this.props.representativeName}
	              </td>
	            </tr>
	    );
	}
});

window.CountyDetailViewDistrictRowComponent = CountyDetailViewDistrictRowComponent;