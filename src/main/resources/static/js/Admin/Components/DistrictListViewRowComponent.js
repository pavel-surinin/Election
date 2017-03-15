var DistrictListViewRowComponent = React.createClass({
  componentDidMount: function () {
    $(this.refs.delete).tooltip();
    $(this.refs.edit).tooltip();
  },
  onHandleDeleteClick : function(){
    this.props.onHandleDelete(this.props.id,this.props.name);
  } ,
  onHandleUploadCanClick : function(){
    this.props.onHandleDelete({

    });
  } ,
  render: function() {
    return (
            <tr>
              <td className="small">
                {this.props.name}
              </td>
              <td className="small">
                {this.props.adress}
              </td>
              <td className="small">
                {this.props.countyName}
              </td>
              <td className="small">
                {this.props.representativeName}
              </td>
              <td>
                <div>
                &nbsp;
                <a href={'#/admin/district/edit/' + this.props.id} ref="edit" data-toggle="tooltip2" title="Redaguoti" id={'edit-district-' + this.props.id} type="button " className="btn btn-primary btn-sm fa fa-pencil"></a>
                &nbsp;
                <a href={'#confirmationModal' + this.props.id}  data-toggle="tooltip1" ref="delete" title="Ištrinti" type="button" id={'confirm-delete-button-' + this.props.id} className="btn btn-danger btn-sm fa fa-trash" data-toggle="modal"></a>
                  <div id={'confirmationModal' + this.props.id} className="modal fade" role="dialog">
                    <div className="modal-dialog">
                      <div className="modal-content">
                        <div className="modal-header">
                           <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                        <div className="modal-body">
                          <h5 className="modal-title">Ar tikrai norite ištrinti {this.props.name} apylinkę?</h5>
                        </div>
                        <div className="modal-footer">
                           <div className="btn-group">
                              <button
                                type="button" onClick={this.onHandleDeleteClick} id={'delete-district-' + this.props.id} className="btn btn-default" data-dismiss="modal">Taip
                              </button>
                              <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Ne</button>
                           </div>
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

window.DistrictListViewRowComponent = DistrictListViewRowComponent;
