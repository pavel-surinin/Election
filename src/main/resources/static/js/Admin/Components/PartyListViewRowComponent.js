var  PartyListViewRowComponent= React.createClass({
  componentDidMount: function () {
    $(this.refs.delete).tooltip();
    $(this.refs.edit).tooltip();
    $(this.refs.info).tooltip();
    $(this.refs.details).tooltip();
  },
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('admin/party/' + id);
    };
  },
  delete : function(){
    this.props.onHandleDelete(this.props.id, this.props.name);
  },
  render: function() {
    return (
            <tr>
              <td className="small">
                {this.props.partyNumber}
              </td>
              <td className="small">
                {this.props.name}
              </td>
              <td className="small">
                <button
                  onClick={this.handleDetailsClick(this.props.id)}
                  id={'details-button-' + this.props.id}
                  className='btn btn-info btn-sm fa fa-info'
                  ref="info"
                  title="Detaliau"
                  role='button'
                  >
                </button>
                &nbsp;
                <a
                  href={'#/admin/party/edit/' + this.props.id}
                  data-toggle="tooltip2"
                  id={'edit-button-' + this.props.id}
                  title="Atnaujinti partijos informaciją"
                  type="button"
                  ref='edit'
                  className="btn btn-primary btn-sm fa fa-pencil">
                </a>
                &nbsp;
                <a href={"#confirmationModal" + this.props.id}  data-toggle="tooltip1" ref="delete" title="Ištrinti" type="button" id={'delete-button-' + this.props.id} className="btn btn-danger btn-sm fa fa-trash" data-toggle="modal"></a>
                  <div id={'confirmationModal' + this.props.id} className="modal fade" role="dialog">
                    <div className="modal-dialog">
                      <div className="modal-content">
                        <div className="modal-header">
                          <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                        <div className="modal-body">
                          <h5 className="modal-title">Ar tikrai norite ištrinti {this.props.name} partiją?</h5>
                        </div>
                        <div className="modal-footer">
                          <div className="btn-group">
                            <button
                                type="button" onClick={this.delete} id={'delete-button-' + this.props.id} className="btn btn-default" data-dismiss="modal">Taip
                            </button>
                            <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Ne</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
