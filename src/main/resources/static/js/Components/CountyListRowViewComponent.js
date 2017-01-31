var CountyListRowViewComponent = React.createClass({

	handleDetailsClick: function(id){
	    var self = this;
	    return function() {
	      self.context.router.push('/admin/county/' + id);
	    };
	  },
	  
	handleEditClick: function(id){
	  var self = this;
	  return function () {
		self.context.router.push('/admin/county/edit/' + id);  
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
                <button onClick={this.handleDetailsClick(this.props.id)} type="button" className="btn btn-info btn-sm fa fa-info"></button>  
              </td>
              <td>
	              <button onClick={this.handleEditClick(this.props.id)} type="button" className="btn btn-primary btn-sm fa fa-pencil"></button>
	              &nbsp;
	              <a href="#/admin/county">
	                <button type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
	              </a>
              </td>
            </tr>
    );
  }

});

CountyListRowViewComponent.contextTypes = {
		  router: React.PropTypes.object.isRequired,
		};
window.CountyListRowViewComponent = CountyListRowViewComponent;
