var CountyListRowViewComponent = React.createClass({

	onHandleDeleteClick: function () {
		this.props.onHandleDelete(this.props.id);
	},

	handleDetailsClick: function(id){
	    var self = this;
	    return function() {
	      self.context.router.push('/admin/county/' + id);
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
                <button onClick={this.handleDetailsClick(this.props.id)} className='btn btn-primary btn-sm' role='button'>Detaliau</button>
              </td>
              <td>
                <button type="button" className="btn btn-primary btn-sm">Naujinti</button>
                <a className="btn btn-danger" onClick={this.onHandleDeleteClick}><i className="fa fa-trash-o fa-lg"></i>Trinti</a>
              </td>
            </tr>
    );
  }

});

CountyListRowViewComponent.contextTypes = {
		  router: React.PropTypes.object.isRequired,
		};
window.CountyListRowViewComponent = CountyListRowViewComponent;
