var CountyContainer = React.createClass({
  getInitialState: function() {
    return {
      countyList : [],
      isLoading : true,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
      .get('/county')
      .then(function(response){
        self.setState({
          county :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('CountyContainer.componentWillMount.axios.get.county', err);
      });
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.countyList);
      return (
        <CountyListViewComponent countyList={this.state.countyList}/>
      );
    }
  }
});

window.CountyContainer = CountyContainer;
