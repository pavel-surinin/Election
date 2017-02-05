var DistrictRespesentativeListViewRowComponent = React.createClass({
	handleEditClick: function(id){
		  var self = this;
		  return function () {
			self.context.router.push('/admin/representative/edit/' + id);  
		  };
		},
		
  render: function() {
    return (
            <tr>
              <td className="small">
                {this.props.id}
              </td>
              <td className="small">
                {this.props.name}
              </td>
              <td className="small">
                {this.props.surname}
              </td>
              <td className="small">
                {this.props.districtName}
              </td>

            </tr>
    );
  }

});

window.DistrictRespesentativeListViewRowComponent = DistrictRespesentativeListViewRowComponent;
