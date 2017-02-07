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
  onFileChange : function(){
    this.props.onFileChange(this.refs.file.files[0], this.props.id);
  },
render: function() {
  var addButtonStyle = styles.toggle(this.props.members);
  var fileErrorMesagesShow = validation.showMsg(this.props.fileErrorMesages);
  var succesMessage = alerts.showSucces(this.props.succesMessage);
  return (
            <tr>
              <td className='small'>
                {this.props.id}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                <a href={'#/admin/county/' + this.props.id} ref="info" title="Detali informacija" id={'details-button-' + this.props.id} className='btn btn-info btn-sm fa fa-info' role='button'></a>
                &nbsp;
                <a href={'#/admin/county/edit/' + this.props.id} data-toggle="tooltip2" ref="edit" title="Redaguoti" type="button " id={'edit-button-' + this.props.id} className="btn btn-primary btn-sm fa fa-pencil"></a>
                &nbsp;
                <div id={this.props.id} className="modal fade" role="dialog">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" id="modal-close-button" className="close" data-dismiss="modal">&times;</button>
                        <h5 className="modal-title">Prisegti kandidatų sąrašą: {this.props.name} apygarda</h5>
                      </div>
                      <div className="modal-body">
                        <form onSubmit={this.props.onHandleFormAddSingleCandSubmit} role="form">

                            <input className="col-xs-10 btn-bs-file btn btn-sm btn-primary btn-outline" onChange={this.onFileChange} ref="file" type="file" name="file" id="file-select"/>
                            <button id={'add-county-single-list-' + this.props.id} className='btn btn-success btn-outline col-xs-1 col-xs-offset-1'>
                            <i className="fa fa-check" aria-hidden="true"></i>
                            </button><br/>
                            <br/>
                        </form>
                        {fileErrorMesagesShow}
                        {succesMessage}
                      </div>
                    </div>
                  </div>
                </div>
                <button onClick={this.onHandleDeleteClick} data-toggle="tooltip1" ref="delete" title="Ištrinti" type="button" id={'delete-button-' + this.props.id} className="btn btn-danger btn-sm fa fa-trash"></button>
                &nbsp;
                <button
                onClick={this.props.onHandleAddClick}
                ref="add"
                data-toggle="tooltip2"
                title="Pridėti Kandidatų sąrašą"
                type="button"
                id={'add-button-' + this.props.id}
                className="btn btn-success btn-sm fa fa-plus"
                data-toggle="modal"
                data-target={'#' + this.props.id}
                style={addButtonStyle}>
                </button>
              </td>
            </tr>
      );
    }

});

CountyListRowViewComponent.contextTypes = {
      router: React.PropTypes.object.isRequired,
    };
window.CountyListRowViewComponent = CountyListRowViewComponent;
