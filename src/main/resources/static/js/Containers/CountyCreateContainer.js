var CountyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
    };
  },
  onHandleSubmit : function(event){
    var self = this;
    event.preventDefault();
    axios
      .post('/county')
      .then(function(response){
        console.log(response);
        self.context.router.push('/county');
      })
      .catch(function(err){
        console.error('CountyCreateContainer.onHandleSubmit.axios', err);
      });
  },
    onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  render: function() {
    return (
      <CountyCreateContainer
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSubmit={this.onHandleSubmit}
       countyList={this.state.countyList}

       action='Sukurti'
       />
    );
  }

});

CountyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.CountyCreateContainer = CountyCreateContainer ;
