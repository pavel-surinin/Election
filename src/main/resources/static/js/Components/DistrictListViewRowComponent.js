var DistrictListViewRowComponent = React.createClass({
  
	handleEditClick: function(id){
		  var self = this;
		  return function () {
			self.context.router.push('/admin/district/edit/' + id);  
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
                {this.props.adress}
              </td>
              <td>
                {this.props.countyName}
              </td>
              <td>
                {this.props.representativeName}
              </td>
              <td>
              <button onClick={this.handleEditClick(this.props.id)} type="button" className="btn btn-primary btn-sm fa fa-pencil"></button>
                &nbsp;
                <button type="button" onClick={this.delete} className="btn btn-danger btn-sm fa fa-trash"></button>  
              </td>
            </tr>
    );
  }
});

window.DistrictListViewRowComponent = DistrictListViewRowComponent;
