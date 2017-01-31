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
              <td>
                {this.props.id}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                {this.props.surname}
              </td>
              <td>
                {this.props.districtName}
              </td>
              <td>
	              <button onClick={this.handleEditClick(this.props.id)} type="button" className="btn btn-primary btn-sm fa fa-pencil"></button>
	              &nbsp;
	              <a href="#/admin/representative">
	                <button type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
	              </a>
              </td>
            </tr>
    );
  }

});

window.DistrictRespesentativeListViewRowComponent = DistrictRespesentativeListViewRowComponent;
