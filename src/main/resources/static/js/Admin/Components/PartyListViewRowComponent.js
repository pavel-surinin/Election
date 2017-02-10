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
    this.props.onHandleDelete(this.props.id);
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
                <button
                  onClick={this.delete}
                  id={'delete-button' + this.props.id}
                  className='btn btn-danger btn-sm fa fa-trash-o'
                  ref="delete"
                  title="Ištrinti"
                  role='button'
                  >
                </button>

              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
