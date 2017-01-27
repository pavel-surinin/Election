var  PartyListViewRowComponent= React.createClass({
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
                <button onClick={this.handleDetailsClick(this.props.id)} className='btn btn-primary btn-sm' role='button'>Detaliau</button>
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
