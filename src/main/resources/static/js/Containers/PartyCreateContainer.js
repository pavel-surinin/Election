var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
    
    };
  },
  componentWillMount: function() {
    this.setState({nameClone : false});
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    var self = this;
    event.preventDefault();
    var partyName = {name: this.state.name.trim()};
    var successAlertText = ('Partija sukurta');
    axios
      .post('/party', partyName)
      .then(function(response){
        console.log(response);
        self.context.router.push('/admin/party');
      })
      .catch(function(err){
        console.error('PartyCreateContainer.onHandleSubmit.axios', err);
        self.setState({nameClone : true});
      });
  },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSubmit={this.onHandleSubmit}
       nameClone={this.state.nameClone}
       action='Sukurti'
       />
    );
  }
});
PartyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyCreateContainer = PartyCreateContainer ;
