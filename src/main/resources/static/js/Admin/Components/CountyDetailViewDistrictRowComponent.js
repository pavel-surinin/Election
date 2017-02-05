var CountyDetailViewDistrictRowComponent = React.createClass({
	render: function(){
		return (
				<tr>
	              <td className='small'>
	                {this.props.name}
	              </td>
	              <td className='small'>
	                {this.props.representativeName}
	              </td>
	            </tr>
	    );
	}
});

window.CountyDetailViewDistrictRowComponent = CountyDetailViewDistrictRowComponent;
