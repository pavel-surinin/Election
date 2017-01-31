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
                <button onClick={this.handleDetailsClick(this.props.id)} id={'details-button-' + this.props.id} className='btn btn-primary btn-sm' role='button'>Detaliau</button>
              </td>
              <td>
								&nbsp;
								<button data-toggle="tooltip2" title="Pridėti Kandidatų sąrašą" type="button" id={'add-button-' + this.props.id} className="btn btn-success btn-sm fa fa-plus"></button>
								&nbsp;
                <a href={'#/admin/county/edit/' + this.props.id} data-toggle="tooltip2" title="Redaguoti" type="button " id={'edit-button-' + this.props.id} className="btn btn-primary btn-sm fa fa-pencil"></a>
                &nbsp;
                <button onClick={this.onHandleDeleteClick} data-toggle="tooltip1" title="Ištrinti" type="button" id={'delete-button-' + this.props.id} className="btn btn-danger btn-sm fa fa-trash"></button>
              </td>
            </tr>
    );
  }

});

CountyListRowViewComponent.contextTypes = {
		  router: React.PropTypes.object.isRequired,
		};
window.CountyListRowViewComponent = CountyListRowViewComponent;
