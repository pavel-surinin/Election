var  PartyListViewRowComponent= React.createClass({
  componentDidMount: function () {
    $(this.refs.delete).tooltip();
    $(this.refs.edit).tooltip();
    $(this.refs.info).tooltip();
  },
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('admin/party/' + id);
    };
  },
  render: function() {
    return (
            <tr>
              <td>
                {this.props.partyNumber}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                <button onClick={this.handleDetailsClick(this.props.id)} ref="info" title="Detaliau" id={'details-button-' + this.props.id} className='btn btn-info btn-sm fa fa-info' role='button'></button>
                &nbsp;
              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
