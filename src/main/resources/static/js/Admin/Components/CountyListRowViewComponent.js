var CountyListRowViewComponent = React.createClass({
	componentDidMount: function () {
		$(this.refs.delete).tooltip();
		$(this.refs.edit).tooltip();
		$(this.refs.info).tooltip();
		$(this.refs.add).tooltip();
	},

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
              <td className='small'>
                {this.props.id}
              </td>
              <td className='small'>
                {this.props.name}
              </td>
            <td>
								&nbsp;
								<button
									ref="add"
									data-toggle="tooltip2"
									title="Pridėti Kandidatų sąrašą"
									type="button"
									id={'add-button-' + this.props.id}
									className="btn btn-success btn-sm fa fa-plus"
									data-toggle="modal"
                  data-target={'#' + this.props.id}>
								</button>
								&nbsp;
								<button onClick={this.handleDetailsClick(this.props.id)} ref="info" title="Detali informacija" id={'details-button-' + this.props.id} className='btn btn-info btn-sm fa fa-info' role='button'></button>
								&nbsp;
                <a href={'#/admin/county/edit/' + this.props.id} data-toggle="tooltip2" ref="edit" title="Redaguoti" type="button " id={'edit-button-' + this.props.id} className="btn btn-primary btn-sm fa fa-pencil"></a>
                &nbsp;
                <div id={this.props.id} className="modal fade" role="dialog">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" id="modal-close-button" className="close" data-dismiss="modal">&times;</button>
                        <h3 className="modal-title text-center">Prisegti kandidatų sąrašą: {this.props.name} apygarda</h3>
                      </div>
                      <div className="modal-body">
                        // TODO input form here
                      </div>
                      <div className="modal-footer">
                        <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Uždaryti</button>
                      </div>
                    </div>
                  </div>
                </div>
								<button onClick={this.onHandleDeleteClick} data-toggle="tooltip1" ref="delete" title="Ištrinti" type="button" id={'delete-button-' + this.props.id} className="btn btn-danger btn-sm fa fa-trash"></button>
							</td>
            </tr>
    	);
  	}

});

CountyListRowViewComponent.contextTypes = {
		  router: React.PropTypes.object.isRequired,
		};
window.CountyListRowViewComponent = CountyListRowViewComponent;
