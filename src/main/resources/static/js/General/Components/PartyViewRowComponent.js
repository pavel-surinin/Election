var PartyViewRowComponent = React.createClass({
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('party/' + id);
    };
  },

  render: function() {
    return (
      <tr>
        <td className="small">
          {this.props.partyNumber}
        </td>
        <td className="small">
        <a href={'#/party/' + this.props.id}>  {this.props.name} </a>
        </td>
        <td>
          {this.props.members}
        </td>
      </tr>
    );
  }
});
PartyViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyViewRowComponent = PartyViewRowComponent;
